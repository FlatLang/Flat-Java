package nova.java.nodewriters;

import net.fathomsoft.nova.tree.exceptionhandling.Throw;

public abstract class ThrowWriter extends ValueWriter
{
	public abstract Throw node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		return builder.append("throw ").append(getWriter(node().getExceptionInstance()).writeExpression());
	}
}