package flat.java.nodewriters;

import flat.tree.*;

public abstract class InitializationMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract InitializationMethod node();
	
	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		builder.append("init_");

		getWriter(node().getParentClass()).writeName(builder);

		return builder;
	}
}