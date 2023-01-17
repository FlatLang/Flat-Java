package flat.java.nodewriters;

import flat.tree.Closure;
import flat.tree.Identifier;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter {
    public abstract Identifier node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder) {
        if (node() != node().getReturnedNode() && node().getReturnedNode() instanceof Closure && !node().isAccessed()) {
            getWriter((Closure)node().getReturnedNode()).writeLambdaParams(builder);
        }

        writeUseExpression(builder);
        writeAccessedExpression(builder);

        return builder;
    }

    public StringBuilder writeUseExpression(StringBuilder builder) {
        return writeName(builder).append(writeArrayAccess());
    }

    public final StringBuilder writeName() {
        return writeName(new StringBuilder());
    }

    public final StringBuilder writeName(StringBuilder builder) {
        return writeName(builder, null);
    }

    public StringBuilder writeName(StringBuilder builder, String name) {
        name = name != null ? name : node().getName();

        switch (name) {
            case "arguments": return builder.append("_java_arguments");
            case "public": return builder.append("_java_public");
            case "private": return builder.append("_java_private");
            case "package": return builder.append("_java_package");
            case "class": return builder.append("_java_class");
            case "default": return builder.append("_java_default");
            case "case": return builder.append("_java_case");
            case "function": return builder.append("_java_function");
            case "char": return builder.append("_java_char");
            default: return builder.append(name);
        }
    }

    public final StringBuilder writeOptionalName()
    {
        return writeOptionalName(new StringBuilder());
    }

    public final StringBuilder writeOptionalName(StringBuilder builder)
    {
        return writeOptionalName(builder, null);
    }

    public StringBuilder writeOptionalName(StringBuilder builder, String name)
    {
        return writeName(builder, name).append("_optional");
    }

}