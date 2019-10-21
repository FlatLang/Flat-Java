package nova.java.nodewriters;

import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Value;

public abstract class ValueWriter extends NodeWriter
{
	public abstract Value node();
	
	public StringBuilder writeUseExpression()
	{
		return writeUseExpression(new StringBuilder());
	}
	
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		return builder.append(writeArrayAccess());
	}
	
	public StringBuilder writeArrayAccess()
	{
		return writeArrayAccess(new StringBuilder());
	}
	
	public StringBuilder writeArrayAccess(StringBuilder builder)
	{
		if (node().arrayAccess != null)
		{
			return getWriter(node().arrayAccess).writeExpression(builder);
		}
		
		return builder;
	}
	
	public final StringBuilder writeType()
	{
		return writeType(new StringBuilder());
	}
	
	public final StringBuilder writeType(boolean space)
	{
		return writeType(new StringBuilder(), space);
	}
	
	public final StringBuilder writeType(StringBuilder builder)
	{
		return writeType(builder, true);
	}
	
	public final StringBuilder writeType(StringBuilder builder, boolean space)
	{
		return writeType(builder, space, true);
	}
	
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive)
	{
		if (node().getType() == null)
		{
			builder.append("void");
		}
		else if (convertPrimitive && node().isPrimitiveType())
		{
			switch (node().getType())
			{
				case "Byte":
					builder.append("byte");
					break;
				case "Char":
					builder.append("char");
					break;
				case "Short":
					builder.append("short");
					break;
				case "Int":
					builder.append("int");
					break;
				case "Long":
					builder.append("long");
					break;
				case "Float":
					builder.append("float");
					break;
				case "Double":
					builder.append("double");
					break;
				case "Bool":
					builder.append("boolean");
					break;
			}
		}
		else if (node().isExternalType())
		{
			builder.append(node().getType());
		}
		else
		{
			ClassDeclaration c = node().getTypeClass();
			
			if (c == null) {
				builder.append("BLOOP");
			} else {
				getWriter(node().getTypeClass()).writeName(builder);
			}
		}
		
		writeArrayDimensions(builder);
		
		if (space)
		{
			builder.append(' ');
		}
		
		return builder;
	}
	
	public StringBuilder writeArrayDimensions()
	{
		return writeArrayDimensions(new StringBuilder());
	}
	
	public StringBuilder writeArrayDimensions(StringBuilder builder)
	{
		for (int i = 0; i < node().getArrayDimensions(); i++)
		{
			builder.append("[]");
		}
		
		return builder;
	}
}