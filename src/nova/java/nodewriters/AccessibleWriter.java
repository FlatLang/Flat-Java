package nova.java.nodewriters;

import net.fathomsoft.nova.tree.Accessible;
import net.fathomsoft.nova.tree.Value;

import static nova.java.nodewriters.Writer.getWriter;

public interface AccessibleWriter
{
	public abstract Accessible node();
	
	default StringBuilder writeAccessedExpression()
	{
		return writeAccessedExpression(new StringBuilder());
	}
	
	default StringBuilder writeAccessedExpression(boolean dot)
	{
		return writeAccessedExpression(new StringBuilder(), dot);
	}
	
	default StringBuilder writeAccessedExpression(StringBuilder builder)
	{
		return writeAccessedExpression(builder, true);
	}
	
	default StringBuilder writeAccessedExpression(StringBuilder builder, boolean dot)
	{
		if (node().doesAccess())
		{
			if (dot)
			{
				builder.append('.');
			}
			
			builder.append(getWriter(node().getAccessedNode()).writeExpression());
		}
		
		return builder;
	}
	
	default StringBuilder writeUntil(Accessible stopBefore)
	{
		return writeUntil(new StringBuilder(), stopBefore);
	}
	
	default StringBuilder writeUntil(StringBuilder builder, Accessible stopBefore)
	{
		Value current = node().toValue();
		
		while (current != null && current != stopBefore)
		{
			if (current != node())
			{
				builder.append('.');
			}
			
			getWriter(current).writeUseExpression(builder);
			
			current = ((Accessible)current).getAccessedNode();
		}
		
		return builder;
	}
}