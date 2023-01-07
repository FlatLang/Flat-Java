package flat.java.nodewriters;

import flat.tree.*;

public abstract class ConstructorWriter extends BodyMethodDeclarationWriter
{
	public abstract Constructor node();
	
	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive)
	{
		return builder;
	}
	
	@Override
	public StringBuilder writeStatic(StringBuilder builder)
	{
		return builder;
	}
	
	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		return getWriter(node().getParentClass()).writeName(builder);
	}
}