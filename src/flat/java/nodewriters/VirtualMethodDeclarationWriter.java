package flat.java.nodewriters;

import flat.tree.*;

public abstract class VirtualMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract VirtualMethodDeclaration node();

	@Override
	public StringBuilder writeName(StringBuilder builder, String name) {
		return getWriter(node().base).writeName(builder, name);
	}
}