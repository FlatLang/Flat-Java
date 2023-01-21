package flat.java.nodewriters;

import flat.tree.ExtendedClass;

public abstract class ExtendedClassWriter extends IValueWriter
{
	public abstract ExtendedClass node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		return writeType(builder, false);
	}
}