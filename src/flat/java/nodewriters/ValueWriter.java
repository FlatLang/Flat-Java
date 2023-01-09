package flat.java.nodewriters;

import flat.tree.ClassDeclaration;
import flat.tree.FlatMethodDeclaration;
import flat.tree.Value;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;

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
		return writeType(builder, space, true, false);
	}
	
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive)
	{
		if (node().isNative()) {
			if (node().isPrimitiveType()) {
				writePrimitiveType(builder, boxPrimitive);
			} else {
				builder.append(node().getType());
			}
		} else if (node().isGenericType()) {
			builder.append(node().getGenericTypeParameter().getName());
		} else if (node().getType() == null) {
			builder.append("void");
		} else if (convertPrimitive && node().isPrimitiveType()) {
			writePrimitiveType(builder, boxPrimitive);
		} else if (node().isExternalType()) {
			builder.append(node().getType());
		} else {
			ClassDeclaration c = node().getTypeClass();
			
			if (c == null) {
				if (node() instanceof FlatMethodDeclaration) {
					GenericTypeParameter param = ((FlatMethodDeclaration)node()).getMethodGenericTypeParameterDeclaration().getParameter(node().getType());

					if (param != null) {
						builder.append(param.getDefaultType());
					} else {
						builder.append("BLOOP");
					}
				} else {
					builder.append("BLOOP");
				}
			} else {
				getWriter(node().getTypeClass()).writeName(builder);
			}
		}

		writeGenericArguments(builder);
		writeArrayDimensions(builder);
		
		if (space)
		{
			builder.append(' ');
		}
		
		return builder;
	}

	public StringBuilder writeGenericArguments(StringBuilder builder) {
		GenericTypeArgumentList args = node().getGenericTypeArgumentList();

		if (args != null && args.getNumVisibleChildren() > 0) {
			builder.append("<");

			for (int i = 0; i < args.getNumVisibleChildren(); i++) {
				if (i > 0) builder.append(", ");

				getWriter(args.getVisibleChild(i)).writeExpression(builder);
			}

			builder.append(">");
		}

		return builder;
	}

	private StringBuilder writePrimitiveType(StringBuilder builder, boolean boxPrimitive) {
		if (boxPrimitive) {
			switch (node().getType())
			{
				case "Byte":
					builder.append("java.lang.Byte");
					break;
				case "Char":
					builder.append("java.lang.Character");
					break;
				case "Short":
					builder.append("java.lang.Short");
					break;
				case "Int":
					builder.append("java.lang.Integer");
					break;
				case "Long":
					builder.append("java.lang.Long");
					break;
				case "Float":
					builder.append("java.lang.Float");
					break;
				case "Double":
					builder.append("java.lang.Double");
					break;
				case "Bool":
					builder.append("java.lang.Boolean");
					break;
			}
		} else {
			switch (node().getType()) {
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