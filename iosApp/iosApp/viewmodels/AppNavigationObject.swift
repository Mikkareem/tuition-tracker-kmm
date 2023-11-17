import Foundation
import SwiftUI

@MainActor class AppNavigationObject: ObservableObject {
    @Published var studentsTabNavigationPath: NavigationPath = NavigationPath()
    @Published var activitiesTabNavigationPath: NavigationPath = NavigationPath()
    
    func addStudentPath(_ path: any Hashable) {
        studentsTabNavigationPath.append(path)
    }
    
    func addActivitiesPath(_ path: any Hashable) {
        activitiesTabNavigationPath.append(path)
    }
    
    func clearStudentsTabPaths() {
        studentsTabNavigationPath.removeLast(studentsTabNavigationPath.count)
    }
    
    func clearActivitiesTabPaths() {
        activitiesTabNavigationPath.removeLast(studentsTabNavigationPath.count)
    }
}
