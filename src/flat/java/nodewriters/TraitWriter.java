package flat.java.nodewriters;

import flat.tree.Trait;

public abstract class TraitWriter extends ClassDeclarationWriter
{
	public abstract Trait node();

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive)
	{
		return builder.append("interface").append(space ? ' ' : "");
	}

	@Override
	public StringBuilder writeSignature(StringBuilder builder) {
		writeVisibility(builder).append(writeStatic());

		writeType(builder).append(writeName());
		return writeGenericTypeParametersDeclaration(builder);
	}

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeSignature(builder).append(" {\n");

		getWriter(node().getPropertyMethodList()).write(builder);
		getWriter(node().getMethodList()).write(builder);

		return builder.append("}\n");
	}
}