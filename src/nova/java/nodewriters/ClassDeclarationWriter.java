package nova.java.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ClassDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract ClassDeclaration node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeSignature(builder).append("\n{\n");
		
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
		for (String c : new String[] { "nova/Object", "nova/String", "nova/io/Console", "nova/datastruct/list/Array",
			"nova/time/Date", "nova/math/Math", "nova/datastruct/Node", "nova/primitive/number/Int", "nova/primitive/number/Double",
			"nova/primitive/number/Byte", "nova/primitive/number/Short", "nova/primitive/number/Long", "nova/primitive/number/Float" })
		{
			if (node().getClassLocation().equals(c))
			{
				return builder.append("Nova").append(node().getName());
			}
		}
		
		return super.writeName(builder);
	}
}