//
//  ContentView.swift
//  Storely
//
//  Created by Michał Chruścielski on 29/01/2022.
//

import SwiftUI

struct ContentView: View {
    
    @State private var selection: Tab = .workshop
    //@EnvironmentObject var isLoggedIn: Bool
    var isLoggedIn = true
    
    enum Tab {
        case workshop
        case rentals
        case settings
    }
    
    var body: some View {
        TabView(selection: $selection) {
            WorkshopView().tabItem { /*@START_MENU_TOKEN@*/Text("Tab Label 1")/*@END_MENU_TOKEN@*/ }.tag(Tab.workshop)
            Text("Tab Content 2").tabItem { /*@START_MENU_TOKEN@*/Text("Tab Label 2")/*@END_MENU_TOKEN@*/ }.tag(Tab.rentals)
        }
        .fullScreenCover(isPresented: .constant(!isLoggedIn)) {
            LoginView()
        }
              
    }
        
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
