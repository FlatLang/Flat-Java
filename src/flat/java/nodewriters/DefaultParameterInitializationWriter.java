package flat.java.nodewriters;

import flat.tree.*;

public abstract class DefaultParameterInitializationWriter extends NodeWriter
{
	public abstract DefaultParameterInitialization node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		Parameter param = node().parameter;
		
		String optionalName = getWriter(param).writeOptionalName().toString();
		
		return getWriter(param).writeType(builder).append(param.getName()).append(" = ").append(optionalName).append(" == null ? ")
			.append(getWriter(param.getDefaultValue()).writeExpression()).append(" : ")
			.append(optionalName).append(".get()");
	}
}