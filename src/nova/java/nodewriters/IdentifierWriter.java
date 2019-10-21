package nova.java.nodewriters;

import net.fathomsoft.nova.tree.Identifier;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter
{
	public abstract Identifier node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		writeUseExpression(builder);
		writeAccessedExpression(builder);
		
		return builder;
	}
	
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		return writeName(builder).append(writeArrayAccess());
	}
	
	public StringBuilder writeName()
	{
		return writeName(new StringBuilder());
	}
	
	public StringBuilder writeName(StringBuilder builder)
	{
		return builder.append(node().getName());
	}
}