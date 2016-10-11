package nova.java.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class DimensionsWriter extends NodeWriter
{
	public abstract Dimensions node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		node().forEachChild(child -> {
			builder.append('[').append(getWriter(child).writeExpression()).append(']');
		});
		
		return builder;
	}
}