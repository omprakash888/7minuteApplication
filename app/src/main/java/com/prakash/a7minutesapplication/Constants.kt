package com.prakash.a7minutesapplication

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel> {
        var exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(
            1,
            "jumping jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )

        exerciseList.add(jumpingJacks)

        val abdominalCrunch = ExerciseModel(
            2,
            "abdominal crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(abdominalCrunch)

        val lunge = ExerciseModel(
            3,
            "Lunch",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunge)

        val plank = ExerciseModel(
            4,
            "plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val pushUp = ExerciseModel(
            5,
            "Lunge",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushUp)

        val pushUpAndRotation = ExerciseModel(
            6,
            "Push Up And Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        exerciseList.add(pushUpAndRotation)

        val sidePlank = ExerciseModel(
            7,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false
        )
        exerciseList.add(sidePlank)

        val squat = ExerciseModel(
            8,
            "squat",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squat)

        val stepUpInToChair = ExerciseModel(
            9,
            "Step Up Into Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(stepUpInToChair)

        val tricepsDipOnChair = ExerciseModel(
            10,
            "Triceps Dip On Chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        exerciseList.add(tricepsDipOnChair)

        val wallSit = ExerciseModel(
            11,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallSit)

        val highKneesRunningInPlace = ExerciseModel(
            12,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highKneesRunningInPlace)

        return exerciseList
    }
}