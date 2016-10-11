package nova.java.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ConstructorWriter extends BodyMethodDeclarationWriter
{
	public abstract Constructor node();
	
	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive)
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