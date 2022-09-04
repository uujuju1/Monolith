package monolith.blocks.voidf;

import mindustry.gen.*;
import monolith.blocks.modules.*;

public interface VoidfBuilding {
	default VoidfModule voidfModule() {return null;}

	// manipulation of the void value
	default void addVoidf(float void, Building src) {voidfModule().voidf += void;}
	default void subVoidf(float void, Building src) {voidfModule().voidf -= void;}
	default void setVoidf(float void, Building src) {voidfModule().voidf = void; }

	// checks if void transfer is possible
	default boolean acceptsVoidf(float void, Building src) {}
	default boolean outputsVoidf(float void, Building src) {}

	// called when the void value is outside the set range
	default void outOfBounds() {}

	// separate draw function cause organization
	default void drawVoidf(Color color) {}
}