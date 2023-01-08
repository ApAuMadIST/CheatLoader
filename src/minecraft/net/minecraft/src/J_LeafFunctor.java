package net.minecraft.src;

abstract class J_LeafFunctor implements J_Functor {
	public final Object func_27059_b(Object object1) {
		if(!this.func_27058_a(object1)) {
			throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27322_a(this);
		} else {
			return this.func_27063_c(object1);
		}
	}

	protected abstract Object func_27063_c(Object object1);
}
