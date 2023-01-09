package flat.java.nodewriters;

import flat.tree.*;

public abstract class FlatMethodDeclarationWriter extends MethodDeclarationWriter
{
	public abstract FlatMethodDeclaration node();

	public StringBuilder writeParameters(StringBuilder builder) {
		return getWriter(node().getParameterList()).write(builder);
	}
}