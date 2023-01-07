package flat.java.nodewriters;

import flat.tree.generics.GenericTypeArgument;

public abstract class GenericTypeArgumentWriter extends IIdentifierWriter
{
    public abstract GenericTypeArgument node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder)
    {
        return builder.append(node().isPrimitive() ? node().getTypeClass().getName() : node().getType());
    }
}