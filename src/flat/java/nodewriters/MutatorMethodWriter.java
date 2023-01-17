package flat.java.nodewriters;

import flat.tree.MutatorMethod;

public abstract class MutatorMethodWriter extends PropertyMethodWriter
{
	public abstract MutatorMethod node();
	
	@Override
	public StringBuilder writeName(StringBuilder builder, String name)
	{
		builder.append("mutator_");
		
		return super.writeName(builder, name);
	}

	@Override
	public StringBuilder write(StringBuilder builder) {
		if (!node().isDisabled()) {
			return super.write(builder);
		} else {
			return builder;
		}
	}
}