package flat.java.nodewriters;

import flat.tree.*;

public abstract class ReturnWriter extends IValueWriter
{
	public abstract Return node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		builder.append("return");
		
		if (node().getValueNode() != null)
		{
			builder.append(' ');
			
			getWriter(node().getValueNode()).writeExpression(builder);
		}
		
		return builder;
	}
}