package flat.java.nodewriters;

import flat.tree.*;

public abstract class InitializationMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract InitializationMethod node();
	
	@Override
	public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic)
	{
		builder.append("init_");

		getWriter(node().getParentClass()).writeName(builder, name);

		return builder;
	}

	@Override
	public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
		return builder;
	}
}