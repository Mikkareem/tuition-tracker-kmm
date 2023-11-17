import Foundation
import shared

@MainActor class StudentsViewModel: ObservableObject {
    let studentsRepository: StudentsRepository
    
    @Published var studentNames: [Student] = []
    
    init(studentsRepository: StudentsRepository) {
        self.studentsRepository = studentsRepository
    }
    
    func loadStudentNames() async {
        do {
            studentNames = try await studentsRepository.getAllStudents()
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func addStudent(name studentName: String, age studentAge: String, gender studentGender: String, standard studentStandard: String) {
        guard let studentAge = Int32(studentAge, radix: 10) else { return }
        
        Task {
            do {
                try await studentsRepository.addStudent(
                    student: Student(id: -1, name: studentName, age: studentAge, gender: studentGender, standard: studentStandard, remarks: nil)
                )
                await loadStudentNames()
            } catch {
                print(error.localizedDescription)
            }
        }
    }
    
    func deleteStudent(_ student: Student) {
//        let id = student.id
//        Task {
//            do {
//                try await studentsRepository.deleteStudent(id)
//            } catch {
//                print(error.localizedDescription)
//            }
//        }
    }
}
