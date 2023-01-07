package flat.java.nodewriters;

import flat.tree.*;

public abstract class ClassDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract ClassDeclaration node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeSignature(builder).append(" {\n");
		
		getWriter(node().getFieldList()).write(builder).append('\n');
		
		getWriter(node().getConstructorList()).write(builder);
		getWriter(node().getPropertyMethodList()).write(builder);
		getWriter(node().getMethodList()).write(builder);
		
		return builder.append("}\n");
	}
	
	@Override
	public StringBuilder writeSignature(StringBuilder builder)
	{
		super.writeSignature(builder);
		
		if (node().doesExtendClass())
		{
			builder.append(" extends ").append(getWriter(node().getExtendedClassDeclaration()).writeName());
		}
		if (node().getImplementedClassNames().length > 0)
		{
			builder.append(" implements ");
			
			for (int i = 0; i < node().getImplementedClassNames().length; i++)
			{
				if (i > 0)
				{
					builder.append(", ");
				}
				
				builder.append(node().getImplementedClassNames()[i]);
			}
		}
		
		return builder;
	}

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive)
	{
		return builder.append("class").append(space ? ' ' : "");
	}
	
	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		for (String c : new String[]{"flat/Object", "flat/String", "flat/io/Console", "flat/datastruct/list/Array",
			"flat/time/Date", "flat/math/Math", "flat/datastruct/Node", "flat/primitive/number/Int", "flat/primitive/number/Double",
			"flat/primitive/number/Byte", "flat/primitive/number/Short", "flat/primitive/number/Long", "flat/primitive/number/Float"}) {
			if (node().getClassLocation().equals(c)) {
				return builder.append("Flat").append(node().getName());
			}
		}
		
		return super.writeName(builder);
	}
}