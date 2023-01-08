package flat.java.nodewriters;

import flat.tree.generics.GenericTypeArgument;

public abstract class GenericTypeArgumentWriter extends IIdentifierWriter
{
    public abstract GenericTypeArgument node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder)
    {
        if (node().isGenericType()) {
            return builder.append(node().getName());
        }

        return writeType(builder, false, false, true);
    }
}