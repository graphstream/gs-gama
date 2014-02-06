/**
 *  test
 *  Author: Thibaut
 *  Description: 
 */

model test


global {
	
	
	init {
		// Clear all previous senders
		gs_clear_senders;
		
		// Add a new sender on localhost:2001
		gs_add_sender gs_host:"localhost" gs_port:2001 gs_sender_id:"test1";
		
		// Create three nodes
		gs_add_node gs_sender_id:"test1" gs_node_id:"node1";
		gs_add_node gs_sender_id:"test1" gs_node_id:"node2";
		gs_add_node gs_sender_id:"test1" gs_node_id:"node3";
		
		// Create one directed edge and one undirected edge
		gs_add_edge gs_sender_id:"test1" gs_edge_id:"edge1" gs_node_id_from:"node1" gs_node_id_to:"node2" gs_is_directed:true;
		gs_add_edge gs_sender_id:"test1" gs_edge_id:"edge2" gs_node_id_from:"node2" gs_node_id_to:"node3" gs_is_directed:false;
		
		
		// Add attributes on edge
			// A string attribute
		gs_add_edge_attribute gs_sender_id:"test1" gs_edge_id:"edge1" gs_attribute_name:"string" gs_attribute_value:"a string value";
			// A double attribute
		gs_add_edge_attribute gs_sender_id:"test1" gs_edge_id:"edge1" gs_attribute_name:"double" gs_attribute_value:10.0;
			// An integer attribute
		gs_add_edge_attribute gs_sender_id:"test1" gs_edge_id:"edge1" gs_attribute_name:"integer" gs_attribute_value:1;
			// A boolean attribute
		gs_add_edge_attribute gs_sender_id:"test1" gs_edge_id:"edge1" gs_attribute_name:"boolean" gs_attribute_value:true;
			// A list attribute
		gs_add_edge_attribute gs_sender_id:"test1" gs_edge_id:"edge1" gs_attribute_name:"list" gs_attribute_value:[1,2,3];
		
		// Step
		gs_step gs_sender_id:"test1" gs_step_number:1;
		
		// Add attributes on node
			// A string attribute
		gs_add_node_attribute gs_sender_id:"test1" gs_node_id:"node1" gs_attribute_name:"string" gs_attribute_value:"a string value";
			// A double attribute
		gs_add_node_attribute gs_sender_id:"test1" gs_node_id:"node1" gs_attribute_name:"double" gs_attribute_value:10.0;
			// An integer attribute
		gs_add_node_attribute gs_sender_id:"test1" gs_node_id:"node1" gs_attribute_name:"integer" gs_attribute_value:1;
			// A boolean attribute
		gs_add_node_attribute gs_sender_id:"test1" gs_node_id:"node1" gs_attribute_name:"boolean" gs_attribute_value:true;
			// A list attribute
		gs_add_node_attribute gs_sender_id:"test1" gs_node_id:"node1" gs_attribute_name:"list" gs_attribute_value:[1,2,3];
		
		// Step
		gs_step gs_sender_id:"test1" gs_step_number:2;
		
		//Remove edge
		gs_remove_edge gs_sender_id:"test1" gs_edge_id:"edge2";
		
		// Remove node
		gs_remove_node gs_sender_id:"test1" gs_node_id:"node3";
		
		// Remove edge attribute
		gs_remove_edge_attribute gs_sender_id:"test1" gs_edge_id:"edge1" gs_attribute_name:"string";
		
		// Step
		gs_step gs_sender_id:"test1" gs_step_number:3;
		
		// Clear
		//gs_clear gs_sender_id:"test1";
		
		// Close
		//gs_close gs_sender_id:"test1";
		
		// If the previous command has worked, the next one should work too
		//gs_add_sender gs_host:"localhost" gs_port:2001 gs_sender_id:"test1";
	}
}

species Provider {
	
	
	aspect base { 
		draw square(1.5°km) color: rgb([100, 0, 100]) ;
	} 
}

experiment test_gs_extension type: gui {
	output {
		display display_FinalDestinationManager {
			species Provider aspect: base;
		}
	}
}