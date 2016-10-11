package nova.java.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ForEachLoopWriter extends LoopWriter
{
	public abstract ForEachLoop node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		Value hasNextCheck = node().getHasNextCheck();
		
		builder.append("while (").append(getWriter(hasNextCheck).writeExpression()).append(")\n");
		
		node().forEachChild(child -> {
			if (child != node().getArgumentList())
			{
				getWriter(child).write(builder);
			}
		});
		
		return builder;
	}
}