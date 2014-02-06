/**
 *  test
 *  Author: Thibaut
 *  Description: 
 */

model test


global {
	
	
	init {
		gs_clear_senders;
		
		// First test with a clear sender
		gs_add_sender gs_host:"localhost" gs_port:2000 gs_sender_id:"test1";
		gs_clear gs_sender_id:"test1";
		
		// Second test
		gs_add_sender gs_host:"localhost" gs_port:2001 gs_sender_id:"test2";
		
		// Create three nodes
		gs_add_node gs_sender_id:"test2" gs_node_id:"node1";
		gs_add_node gs_sender_id:"test2" gs_node_id:"node2";
		gs_add_node gs_sender_id:"test2" gs_node_id:"node3";
		
		// Create one directed
		gs_add_edge gs_sender_id:"test2" gs_edge_id:"edge1" gs_node_id_from:"node1" gs_node_id_to:"node2" gs_is_directed:true;
		gs_add_edge gs_sender_id:"test2" gs_edge_id:"edge2" gs_node_id_from:"node2" gs_node_id_to:"node3" gs_is_directed:false;

		//gs_step gs_sender_id:"plop" gs_step_number:0;
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