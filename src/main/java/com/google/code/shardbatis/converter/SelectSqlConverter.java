/**
 * 
 */
package com.google.code.shardbatis.converter;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * @author sean.he
 * 
 */
public class SelectSqlConverter extends AbstractSqlConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.code.shardbatis.converter.AbstractSqlConverter#doConvert(net
	 * .sf.jsqlparser.statement.Statement, java.lang.Object, java.lang.String)
	 */
	@Override
	protected Statement doConvert(Statement statement, final Object params,
			final String mapperId) {
		if (!(statement instanceof Select)) {
			throw new IllegalArgumentException(
					"The argument statement must is instance of Select.");
		}
		TableNameModifier modifier = new TableNameModifier(params, mapperId);
		((Select) statement).getSelectBody().accept(modifier);
		return statement;
	}

	private class TableNameModifier  extends TablesNamesFinder{
		private Object params;
		private String mapperId;

		TableNameModifier(Object params, String mapperId) {
			this.params = params;
			this.mapperId = mapperId;
		}

        @Override
        public void visit(Table tableName) {
            super.visit(tableName);
            tableName.setName(convertTableName(tableName.getName(), params, mapperId));
        }
    }

}
