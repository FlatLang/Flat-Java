package flat.java.nodewriters;

import flat.tree.*;

public abstract class ParameterWriter extends LocalDeclarationWriter
{
	public abstract Parameter node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return writeExpression(builder);
	}
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		return writeSignature(builder);
	}
	
	@Override
	public StringBuilder writeSignature(StringBuilder builder)
	{
		if (node().isOptional())
		{
			return writeOptionalType(builder).append(writeOptionalName());
		}
		
		return super.writeSignature(builder);
	}
	
	public StringBuilder writeOptionalType()
	{
		return writeOptionalType(new StringBuilder());
	}
	
	public StringBuilder writeOptionalType(StringBuilder builder)
	{
		builder.append("Optional<");
		super.writeType(builder, false, true, true);
		return builder.append("> ");
	}
	
	public StringBuilder writeOptionalName()
	{
		return writeOptionalName(new StringBuilder());
	}
	
	public StringBuilder writeOptionalName(StringBuilder builder)
	{
		return writeName(builder).append("_optional");
	}
}