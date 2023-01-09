package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameterList;

import java.util.Arrays;
import java.util.Optional;

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

		Arrays.stream(node().getEncapsulatedClasses())
			.map(Writer::getWriter)
			.forEach(w -> w.write(builder).append('\n'));
		
		return builder.append("}\n");
	}
	
	@Override
	public StringBuilder writeSignature(StringBuilder builder)
	{
		writeVisibility(builder);
		writeStatic(builder);
		builder.append("class ");
		writeName(builder);
		writeGenericTypeParametersDeclaration(builder);
		
		if (node().doesExtendClass()) {
			builder.append(" extends ").append(getWriter(node().getExtendedClassDeclaration()).writeName());
		}
		if (node().getImplementedClassNames().length > 0) {
			builder.append(" implements ");
			
			for (int i = 0; i < node().getImplementedClassNames().length; i++) {
				if (i > 0) builder.append(", ");

				String name = node().getImplementedClassNames()[i];
				
				builder.append(name);

				TraitImplementation imp = node().getInterfacesImplementationList().getChildStream()
					.map(t -> (TraitImplementation)t)
					.filter(c -> c.getType().equals(name))
					.findFirst().get();

				GenericTypeArgumentList args = imp.getGenericTypeArgumentList();

				if (args.getNumVisibleChildren() > 0) {
					builder.append("<");

					for (int n = 0; n < args.getNumVisibleChildren(); n++) {
						if (n > 0) builder.append(", ");

						GenericTypeArgument arg = args.getVisibleChild(n);

						getWriter(arg).writeExpression(builder);
					}

					builder.append(">");
				}
			}
		}
		
		return builder;
	}

	public StringBuilder writeGenericTypeParametersDeclaration(StringBuilder builder) {
		GenericTypeParameterList params = node().getGenericTypeParameterDeclaration();

		if (params.getNumVisibleChildren() > 0) {
			builder.append("<");

			for (int i = 0; i < params.getNumVisibleChildren(); i++) {
				if (i > 0) builder.append(", ");

				getWriter(params.getVisibleChild(i)).writeExpression(builder);
			}

			builder.append(">");
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
			"flat/primitive/number/Byte", "flat/primitive/number/Short", "flat/primitive/number/Long", "flat/primitive/number/Float",
			"flat/meta/Class"}) {
			if (node().getClassLocation().equals(c)) {
				return builder.append("Flat").append(node().getName());
			}
		}
		
		return super.writeName(builder);
	}
}