package flat.java.nodewriters;

import flat.tree.AnonymousCompilerMethod;

public abstract class AnonymousCompilerMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract AnonymousCompilerMethod node();

	@Override
	public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
		return builder;
	}

	@Override
	public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic) {
		return super.writeName(builder, name, false);
	}
}