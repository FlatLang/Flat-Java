package flat.java.nodewriters;

import flat.tree.variables.VariableDeclaration;

public abstract class VariableDeclarationWriter extends IIdentifierWriter
{
	public abstract VariableDeclaration node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		return writeSignature(builder);
	}
	
	public StringBuilder writeSignature()
	{
		return writeSignature(new StringBuilder());
	}
	
	public StringBuilder writeSignature(StringBuilder builder)
	{
		return writeType(builder).append(writeName());
	}

	@Override
	public StringBuilder writeName(StringBuilder builder) {
		switch (node().getName()) {
			case "class":
				return super.writeName(builder.append("flat_"));
			default:
				return super.writeName(builder);
		}
	}
}