package nova.java.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class WhileLoopWriter extends LoopWriter
{
	public abstract WhileLoop node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		builder.append("while (").append(getWriter(node().getCondition()).writeExpression()).append(")\n");
		
		return getWriter(node().getScope()).write(builder);
	}
}