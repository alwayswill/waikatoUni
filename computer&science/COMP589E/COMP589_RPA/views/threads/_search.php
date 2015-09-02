<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\RPAThreadsSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="rpathreads-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'pid') ?>

    <?= $form->field($model, 'memory') ?>

    <?= $form->field($model, 'startTime') ?>

    <?= $form->field($model, 'runningTime') ?>

    <?php // echo $form->field($model, 'CPU') ?>

    <?php // echo $form->field($model, 'command') ?>

    <?php // echo $form->field($model, 'stat') ?>

    <?php // echo $form->field($model, 'IP') ?>

    <?php // echo $form->field($model, 'status') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-default']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
