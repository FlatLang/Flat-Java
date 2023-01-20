package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.variables.Array;

public abstract class InstantiationWriter extends IIdentifierWriter
{
	public abstract Instantiation node();
	
	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		return builder.append("new ").append(getWriter(node().getIdentifier()).writeUseExpression());
	}

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context) {
		return super.writeType(builder, space, false, true, context);
	}
}