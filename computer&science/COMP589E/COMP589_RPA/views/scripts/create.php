<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\RPAScripts */

$this->title = 'Create scripts';
$this->params['breadcrumbs'][] = ['label' => 'Rpascripts', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="rpascripts-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
