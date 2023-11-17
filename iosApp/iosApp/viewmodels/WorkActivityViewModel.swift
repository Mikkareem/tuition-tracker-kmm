import Foundation
import shared

@MainActor class WorkActivityViewModel: ObservableObject {
    @Published var activities: [WorkActivity] = []
    
    let workActivitiesRepository: WorkActivitiesRepository
    
    init(workActivitiesRepository: WorkActivitiesRepository) {
        self.workActivitiesRepository = workActivitiesRepository
    }
    
    func addWorkActivity(name: String, description: String, groupKey: String, groupValue: String, createdDate: Date, expirationDate: Date) {
        let workActivity = WorkActivity(
            id: -1, name: name, description: description,
            groupKey: groupKey, groupValue: groupValue,
            createdDate: createdDate.toKotlinXDatetimeLocalDate(),
            expirationDate: expirationDate.toKotlinXDatetimeLocalDate()
        )
        
        Task {
            await assignThisWorkActivity(workActivity)
        }
    }
    
    private func assignThisWorkActivity(_ workActivity: WorkActivity) async {
        do {
            try await workActivitiesRepository.addWorkActivity(workActivity: workActivity)
            await loadWorkActivities()
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func loadWorkActivities() async {
        do {
            activities = try await workActivitiesRepository.getAllWorkActivities()
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func loadAllGenderBasedWorkActivities() async {
        do {
            activities = try await workActivitiesRepository.getAllGenderWorkActivities()
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func loadAllMaleGenderWorkActivities() async {
        do {
            activities = try await workActivitiesRepository.getAllMaleWorkActivities()
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func loadAllFemaleGenderWorkActivities() async {
        do {
            activities = try await workActivitiesRepository.getAllFemaleWorkActivities()
        } catch {
            print(error.localizedDescription)
        }
    }
    
//    func loadAllStandardWorkActivities() async {
//        do {
//            activities = try await workActivitiesRepository.getAllStandardWorkActivities()
//        } catch {
//            print(error.localizedDescription)
//        }
//    }
}
