package flat.java.nodewriters;

import flat.tree.ClassDeclaration;
import flat.tree.FirstClassClosureDeclaration;
import flat.tree.FunctionType;
import flat.tree.Value;
import flat.tree.generics.GenericTypeArgument;

public abstract class GenericTypeArgumentWriter extends IIdentifierWriter
{
    public abstract GenericTypeArgument node();

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context) {
        if (node().getTypeObject() instanceof FunctionType) {
            FirstClassClosureDeclaration closure = ((FunctionType) node().getTypeObject()).closure;

            return getWriter(closure).writeType(builder, space, convertPrimitive, boxPrimitive, context);
        }

        return super.writeType(builder, space, convertPrimitive, boxPrimitive, context);
    }

    @Override
    public StringBuilder writeTypeName(StringBuilder builder, boolean convertPrimitive, boolean boxPrimitive, Value context) {
        ClassDeclaration c = node().getTypeClass();

        if (c == null) {
            return builder.append(node().getName());
        }

        return super.writeTypeName(builder, convertPrimitive, boxPrimitive, context);
    }

    @Override
    public StringBuilder writeExpression(StringBuilder builder) {
        return writeExpression(builder, null);
    }

    public StringBuilder writeExpression(StringBuilder builder, Value context) {
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