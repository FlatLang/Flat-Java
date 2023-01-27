package flat.java.nodewriters;

import flat.tree.Value;
import flat.tree.generics.GenericTypeArgument;

public abstract class GenericTypeArgumentWriter extends IIdentifierWriter
{
    public abstract GenericTypeArgument node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder) {
        return writeExpression(builder, null);
    }

    public StringBuilder writeExpression(StringBuilder builder, Value context) {
        if (node().autoAdded) {
            return builder.append("?");
        }
        if (node().isGenericType()) {
            return writeGenericType(builder, context);
        }

        return writeType(builder, false, false, true, context);
    }

    @Override
    public StringBuilder writeGenericType(StringBuilder builder, Value context) {
        if (context == null) {
            return builder.append(node().getName());
        }

        return super.writeGenericType(builder, context);
    }
}