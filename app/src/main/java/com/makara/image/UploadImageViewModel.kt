import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.makara.data.MakaraRepository
import com.makara.data.local.pref.MakaraPreference
import kotlinx.coroutines.launch
import java.util.UUID

class UploadImageViewModel(private val repository: MakaraRepository, private val makaraPreference: MakaraPreference) : ViewModel() {

    val uploadResult = MutableLiveData<MakaraRepository.PredictionResult>()

    fun uploadImageAndPredict(imageUri: Uri) {
        // Upload image to Firebase Storage
        val filename = UUID.randomUUID().toString()
        val storageRef = FirebaseStorage.getInstance("gs://makara-static-images").reference.child("images/$filename")

        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Send the image URL to the repository for prediction
                    predictImage(downloadUri.toString())
                }
            }
            .addOnFailureListener {
                uploadResult.postValue(MakaraRepository.PredictionResult.Error(it.message ?: "Failed to upload image"))
            }
    }

    private fun predictImage(imageUrl: String) {
        viewModelScope.launch {
            val authToken = obtainFirebaseAuthToken() // Implement this to retrieve the auth token
            if(authToken == null) {
                uploadResult.postValue(MakaraRepository.PredictionResult.Error("Failed to obtain auth token"))
                return@launch
            }
            uploadResult.postValue(repository.sendImageForPrediction(authToken, imageUrl))
        }
    }

    private suspend fun obtainFirebaseAuthToken(): String? {
        // Implement logic to retrieve Firebase Auth token
        return makaraPreference.getAuthToken()
    }
}
