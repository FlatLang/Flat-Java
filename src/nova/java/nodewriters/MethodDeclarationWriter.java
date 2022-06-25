package nova.java.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class MethodDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract MethodDeclaration node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return builder;
	}

	@Override
	public StringBuilder writeName(StringBuilder builder) {
		switch (node().getName()) {
			case "toString":
				return super.writeName(builder.append("nova_"));
			default:
				return super.writeName(builder);
		}
	}
}