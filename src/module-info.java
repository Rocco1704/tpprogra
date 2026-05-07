module Tpconectar {
    requires java.desktop;
    requires junit; // Esto permite usar JUnit
    
    opens test to junit; // Esto permite que JUnit ejecute tus tests
}