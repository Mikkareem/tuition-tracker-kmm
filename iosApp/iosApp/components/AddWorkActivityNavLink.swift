//
//  AddWorkActivityNavLink.swift
//  iosApp
//
//  Created by Irsath Kareem on 14/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct AddWorkActivityNavLink: View {
    @EnvironmentObject private var appNavigation: AppNavigationObject
    
    var body: some View {
//        NavigationLink(value: StudentsNavigation.addStudent) {
//            Text("Add Activity")
//        }

        Button("Add Activity") {
            appNavigation.addActivitiesPath(CustomNavigation.addActivity)
        }
    }
}
