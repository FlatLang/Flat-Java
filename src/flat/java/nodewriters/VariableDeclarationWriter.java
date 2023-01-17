package flat.java.nodewriters;

import flat.tree.Value;
import flat.tree.variables.VariableDeclaration;

public abstract class VariableDeclarationWriter extends IIdentifierWriter
{
	public abstract VariableDeclaration node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		return writeSignature(builder);
	}
	
	public final StringBuilder writeSignature()
	{
		return writeSignature(new StringBuilder());
	}
	
	public final StringBuilder writeSignature(StringBuilder builder) {
		return writeSignature(builder, null);
	}

	public final StringBuilder writeSignature(StringBuilder builder, Value context) {
		return writeSignature(builder, context, null);
	}

	public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
		writeType(builder, true, true, false, context);
		return writeName(builder, name);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder, String name) {
		name = name != null ? name : node().getName();

		switch (name) {
			case "class":
				return super.writeName(builder.append("flat_"), name);
			default:
				return super.writeName(builder, name);
		}
	}
}