<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\RPAThreads */

$this->title = 'Create Rpathreads';
$this->params['breadcrumbs'][] = ['label' => 'Rpathreads', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="rpathreads-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
