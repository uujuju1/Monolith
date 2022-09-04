package monolith.blocks.voidf;

import mindustry.gen.*;
import monolith.blocks.modules.*;

public interface VoidfBuilding {
	default VoidfModule voidfModule() {return null;}

	// manipulation of the void value
	default void addVoidf(float voidf, Building src) {voidfModule().voidf += voidf;}
	default void subVoidf(float voidf, Building src) {voidfModule().voidf -= voidf;}
	default void setVoidf(float voidf, Building src) {voidfModule().voidf = voidf; }

	// checks if void transfer is possible
	default boolean acceptsVoidf(float voidf, Building src) {}
	default boolean outputsVoidf(float voidf, Building src) {}

	// called when the void value is outside the set range
	default void outOfBounds() {}

	// separate draw function cause organization
	default void drawVoidf(Color color) {}
}