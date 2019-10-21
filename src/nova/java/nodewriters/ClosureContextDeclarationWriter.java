package nova.java.nodewriters;

import net.fathomsoft.nova.tree.ClosureContextDeclaration;

public abstract class ClosureContextDeclarationWriter extends LocalDeclarationWriter
{
	public abstract ClosureContextDeclaration node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return builder;
	}
}