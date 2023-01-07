package flat.java.nodewriters;

import flat.tree.*;

public abstract class ImportWriter extends NodeWriter
{
	public abstract Import node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		if (node().isExternal())
		{
			return builder;
		}
		
		return super.write(builder);
	}
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		String path = String.join(".", node().location.substring(0, Math.max(0, node().location.lastIndexOf('/'))).split("[/]"));
		
		path += path.length() == 0 ? "" : '.';
		path += getWriter(node().getClassDeclaration()).writeName();
		
		return builder.append("import ").append(path);
	}
}