import Foundation
import shared

@MainActor class StudentDetailsViewModel: ObservableObject {
    private let studentWorkActivitiesRepository: StudentWorkActivitiesRepository
    
    @Published private(set) var assignedActivities: [AssignedWorkActivity] = []
    
    init(studentWorkActivitiesRepository: StudentWorkActivitiesRepository) {
        self.studentWorkActivitiesRepository = studentWorkActivitiesRepository
    }
    
    func toggleCompletionStatusOfAssignedActivity(_ assignedActivity: AssignedWorkActivity) {
        Task {
            do {
                try await studentWorkActivitiesRepository.updateActivityCompletionStatusForStudent(studentId: assignedActivity.student.id, activityId: assignedActivity.workActivity.id, isCompleted: !assignedActivity.isCompleted)
                await loadStudentAssignedActivities(student: assignedActivity.student)
            } catch {
                print(error.localizedDescription)
            }
        }
    }
    
    func loadStudentAssignedActivities(student: Student) async {
        do {
            self.assignedActivities = try await studentWorkActivitiesRepository.getAllActivitiesForStudent(studentId: student.id)
        } catch {
            print(error.localizedDescription)
        }
    }
}
