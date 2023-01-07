package flat.java.nodewriters;

import flat.tree.*;

public abstract class ContinueWriter extends NodeWriter
{
	public abstract Continue node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		return builder.append("continue");
	}
}